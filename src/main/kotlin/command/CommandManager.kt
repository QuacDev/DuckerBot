package command

import dev.kord.core.Kord
import dev.kord.rest.builder.interaction.GlobalChatInputCreateBuilder
import dev.kord.rest.builder.interaction.integer
import java.util.*
import kotlin.collections.ArrayList

object CommandManager {
    private val commands: ArrayList<Command> = ArrayList()

    suspend fun registerCommand(command: Command, bot: Kord) {
        var hasConflictingNames = false

        if(commands.isNotEmpty()) {
            commands.forEach { registeredCommand: Command ->
                if(registeredCommand.name.equals(command.name, ignoreCase = true)) {
                    println("Command name '${command.name}' has a conflict!")
                    hasConflictingNames = true;
                }
            }
        }

        if(hasConflictingNames) {
            println("Couldn't register command '${command.name}' due to conflicts!")
            return
        }

        commands.add(command)
        bot.createGlobalChatInputCommand (
            command.name, command.description, command.builder
        )

        println("Registered command '${command.name}'")
        println("Now registered ${commands.size} commands")
    }

    fun getCommand(commandName: String) : Command? {
        if(commands.isEmpty()) {
            println("Couldn't find command '$commandName' because the CommandsList is empty!")
            return null
        }

        commands.forEach { command: Command ->
            if(command.name.equals(commandName, ignoreCase = true)) {
                return command
            }
        }

        println("Couldn't find command '$commandName'!")
        return null
    }
}