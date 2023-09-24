import com.google.gson.Gson
import command.Command
import command.CommandManager
import command.commands.Ping
import command.commands.Point
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import java.sql.Connection
import java.sql.DriverManager
import java.time.Instant

object Main {
    val config = Gson().fromJson(object {}.javaClass.getResource("config.json").readText(), Config.Config::class.java)
    val databaseUrl = "jdbc:mysql://${config.dbHostname}:${config.dbPort}/${config.db}?characterEncoding=utf8"
    var connection: Connection? = null
}

suspend fun main(args: Array<String>) {
    val bot = Kord(Main.config.token)

    CommandManager.registerCommand(Ping(), bot)
    CommandManager.registerCommand(Point(), bot)

    bot.on<GuildChatInputCommandInteractionCreateEvent> {
        val command: Command = CommandManager.getCommand(interaction.command.rootName) ?: return@on

        command.handle(this, bot)
    }

    bot.login {
        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent

        println("Bot logged in at: ${Instant.now()}")

         Main.connection = DriverManager.getConnection(Main.databaseUrl, Main.config.dbUsername, Main.config.dbPassword)
        if(Main.connection != null) println("Connected to the database '${Main.config.db}'")
    }
}