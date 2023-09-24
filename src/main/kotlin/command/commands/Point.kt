package command.commands

import command.Command
import core.DatabaseUtils
import core.User
import dev.kord.common.Color
import dev.kord.common.entity.optional.Optional
import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.edit
import dev.kord.core.cache.data.EmbedAuthorData
import dev.kord.core.cache.data.EmbedData
import dev.kord.core.entity.Embed
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.rest.builder.interaction.*
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.builder.message.modify.embed
import io.ktor.network.sockets.*

class Point : Command(
    "point",
    "Point a person",
    {
        user("user", "User you want to point") { required = true }
        integer("points", "Amount of points") { required = true }
        string("reason", "Reason of point") { required = true }
        boolean("private", "Hide it from the public?")
    }
    ) {

    override suspend fun handle(e: GuildChatInputCommandInteractionCreateEvent, bot: Kord) {
        super.handle(e, bot)

        if(Main.connection == null) return

        val user = e.interaction.command.users["user"]!!
        val points = e.interaction.command.integers["points"]!!.toInt()
        val reason = e.interaction.command.strings["reason"]!!
        val private = e.interaction.command.booleans["private"]

        val userData = DatabaseUtils.getUser(user.id.value.toLong())

        userData.points += points

        DatabaseUtils.saveUser(userData)

        val response = if(private == true) e.interaction.deferEphemeralResponseUnsafe() else e.interaction.deferPublicResponseUnsafe()

        val embed = EmbedBuilder()
        embed.author = EmbedBuilder.Author().apply {
            name = "${e.interaction.user.effectiveName} (${e.interaction.user.id})"
            icon = e.interaction.user.avatar!!.cdnUrl.toUrl()
        }

        embed.color = Color(255, 0, 0)

        embed.description = "${user.mention} now has ${userData.points} points - *punishment*"

        embed.field("Reason:", false) {
            reason
        }

        response.edit { embeds = arrayListOf(embed) }
    }
}