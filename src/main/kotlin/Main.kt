import com.google.gson.Gson
import command.Command
import command.CommandManager
import command.commands.Ping
import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import java.time.Instant
import java.util.*

val config = Gson().fromJson(object {}.javaClass.getResource("config.json").readText(), Config.Config::class.java)
val commandManager: CommandManager = CommandManager()

suspend fun main(args: Array<String>) {
    val bot = Kord(config.token)

    commandManager.registerCommand(Ping())

    bot.on<MessageCreateEvent> {
        if(message.author?.isBot == true) return@on
        if(!message.content.startsWith(config.prefix)) return@on

        val contentNP = message.content.replaceFirst(config.prefix, "")
        val commandNameEndIndex: Int = if (contentNP.contains(" ")) contentNP.indexOf(" ") else contentNP.length
        val commandName = contentNP.substring(0, commandNameEndIndex).lowercase()

        println("Command '$commandName' has been ran by user ${message.getAuthorAsMember().id}")

        val command: Command = commandManager.getCommand(commandName) ?: return@on
        command.handle(this, bot)
    }

    bot.login {
        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent
    }
}