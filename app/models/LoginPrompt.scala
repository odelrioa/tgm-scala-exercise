package models

import play.api.libs.json.Json

/**
 * A login prompt is like a simple ad which is displayed after a user logs in.
 * @param id A unique id.
 * @param caption The caption text.
 * @param imageUrl The image url.
 */
case class LoginPrompt(id: Long,
                       caption: String,
                       imageUrl: String,
                       quietTimeInSeconds: Int,
                       lastLoginTimestamp: Long)

object LoginPrompt {
  implicit val loginPromptFormat = Json.format[LoginPrompt]
}