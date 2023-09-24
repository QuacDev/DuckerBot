class Config {
    data class Config(
        val token: String,
        val db: String,
        val dbHostname: String,
        val dbPort: String,
        val dbUsername: String,
        val dbPassword: String
    )
}