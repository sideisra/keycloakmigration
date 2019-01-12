package de.klg71.keycloakmigration.changeControl.actions

import de.klg71.keycloakmigration.model.AddLdap
import de.klg71.keycloakmigration.model.constructAdLdapConfig
import org.apache.commons.codec.digest.DigestUtils

class AddAdLdapAction(
        private val realm: String,
        private val name: String,
        private val config: Map<String, String>) : Action() {


    private val hash = calculateHash()

    private fun calculateHash() =
            StringBuilder().run {
                append(realm)
                append(name)
                config.forEach { key, value ->
                    append(key)
                    append(value)
                }
                toString()
            }.let {
                DigestUtils.sha256Hex(it)
            }!!

    override fun hash() = hash


    override fun execute() {
        client.addLdap(addLdap(), realm)
    }

    private fun addLdap(): AddLdap = AddLdap(name, realm, constructAdLdapConfig(config))

    override fun undo() {
        client.userFederations(realm).find {
            it.name == name
        }?.let {
            client.deleteUserFederation(realm, it.id)
        }
    }


    override fun name() = "AddLdap $name"

}