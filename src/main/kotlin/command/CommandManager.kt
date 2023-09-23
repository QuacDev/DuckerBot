package command

import java.util.*
import kotlin.collections.ArrayList

class CommandManager {
    private val commands: ArrayList<Command> = ArrayList()

    fun registerCommand(command: Command) {
        var hasConflictingNames = false
        var hasConflictingAliases = false;

        if(commands.isNotEmpty()) {
            commands.forEach { registeredCommand: Command ->
                if(registeredCommand.name.equals(command.name, ignoreCase = true)) {
                    println("Command name '${command.name}' has a conflict!")
                    hasConflictingNames = true;
                }

                registeredCommand.aliases.forEach { registeredAlias: String ->
                    command.aliases.forEach { newAlias: String ->
                        if(newAlias.equals(registeredAlias, ignoreCase = true)) {
                            println("The alias '$newAlias' from command '${command.name}' has a conflict with the alias from command '${registeredCommand.name}'")
                            hasConflictingAliases = true;
                        }
                    }
                }
            }
        }

        if(hasConflictingAliases || hasConflictingNames) {
            println("Couldn't register command '${command.name}' due to conflicts!")
            return
        }

        commands.add(command)
    }

    fun getCommand(commandName: String) : Command? {
        if(commands.isEmpty()) {
            println("Couldn't find command '$commandName' because the CommandsList is empty!")
            return null
        }

        commands.forEach { command: Command ->
            if(command.name.equals(commandName, ignoreCase = true) || command.aliases.contains(commandName.lowercase())) {
                return command
            }
        }

        println("Couldn't find command '$commandName'!")
        return null
    }
}