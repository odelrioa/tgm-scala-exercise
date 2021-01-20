package controllers

import akka.http.scaladsl.util.FastFuture

import scala.concurrent.ExecutionContext
import javax.inject.{Inject, Singleton}
import models.LoginPromptRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.util.Random

/**
 * This controller handles the JSON API used by client applications to display login prompts.
 */
@Singleton
class ApiController @Inject()(
  repo: LoginPromptRepository,
  val cc: ControllerComponents
)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  /**
   * Returns the list of existing login prompts.
   * @return A list of login prompts, in JSON format.
   */
  def getLoginPrompts = Action.async { implicit request =>
    repo.list().map { loginPrompts =>
      Ok(Json.toJson(loginPrompts))
    }
  }

  /**
   * Returns an existing login prompt.
   * @param id The unique id.
   * @return A login prompt, in JSON format.
   */
  def getLoginPrompt(id: Long) = Action.async { implicit request =>
    repo.findById(id).map {
      case Some(loginPrompt) => Ok(Json.toJson(loginPrompt))
      case None => NotFound
    }
  }

  /**
   * Returns a random login prompt.
   * @return A login prompt, in JSON format.
   */
  def getRandomLoginPrompt() = Action.async {
    (for {
      loginPromptList <- repo.list()
    } yield {
      val randomId = Random.nextInt(loginPromptList.length)
      val randomLoginPrompt = loginPromptList(randomId)
      Ok(Json.toJson(randomLoginPrompt))
    }) recoverWith {
      case e: Throwable =>
        FastFuture.successful(InternalServerError(Json.obj("error" -> s"something went wrong: $e")))
    }
  }

  /**
   * Returns a random login prompt for an existing user.
   * @param userId The unique user id.
   * @return A login prompt, in JSON format.
   */
  def getRandomLoginPromptForUser(userId: Long) = Action.async {
    for {
      loginPromt <- repo.findById(userId)
    } yield loginPromt match {
      case Some(lp) => {
        val currentTimestamp = System.currentTimeMillis()
        if(lp.quietTimeInSeconds <= 0 ||
          lp.lastLoginTimestamp <= 0 ||
          currentTimestamp - lp.lastLoginTimestamp >= lp.quietTimeInSeconds * 1000) {
          repo.updateLastLogin(lp.id, currentTimestamp)
          Ok(Json.toJson(lp))
        } else {
          Ok(Json.obj("error" -> "Quiet time restriction!"))
        }
      }
      case _ => NotFound(Json.obj("error" -> "Not Found!"))
    }
  }
}
