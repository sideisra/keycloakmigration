package de.klg71.keycloakmigration.changeControl.actions

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.klg71.keycloakmigration.changeControl.actions.client.AddSimpleClientAction
import de.klg71.keycloakmigration.changeControl.actions.client.AssignRoleToClientAction
import de.klg71.keycloakmigration.changeControl.actions.client.DeleteClientAction
import de.klg71.keycloakmigration.changeControl.actions.client.ImportClientAction
import de.klg71.keycloakmigration.changeControl.actions.client.UpdateClientAction
import de.klg71.keycloakmigration.changeControl.actions.client.mapper.AddAudienceMapperAction
import de.klg71.keycloakmigration.changeControl.actions.client.mapper.AddGroupMembershipMapperAction
import de.klg71.keycloakmigration.changeControl.actions.client.mapper.AddMapperAction
import de.klg71.keycloakmigration.changeControl.actions.client.mapper.AddUserAttributeMapperAction
import de.klg71.keycloakmigration.changeControl.actions.client.mapper.AddUserRealmRoleMapperAction
import de.klg71.keycloakmigration.changeControl.actions.clientscope.AddClientScopeAction
import de.klg71.keycloakmigration.changeControl.actions.clientscope.AssignDefaultClientScopeAction
import de.klg71.keycloakmigration.changeControl.actions.group.AddGroupAction
import de.klg71.keycloakmigration.changeControl.actions.group.AssignRoleToGroupAction
import de.klg71.keycloakmigration.changeControl.actions.group.DeleteGroupAction
import de.klg71.keycloakmigration.changeControl.actions.group.RevokeRoleFromGroupAction
import de.klg71.keycloakmigration.changeControl.actions.group.UpdateGroupAction
import de.klg71.keycloakmigration.changeControl.actions.realm.AddRealmAction
import de.klg71.keycloakmigration.changeControl.actions.realm.DeleteRealmAction
import de.klg71.keycloakmigration.changeControl.actions.realm.UpdateRealmAction
import de.klg71.keycloakmigration.changeControl.actions.role.AddRoleAction
import de.klg71.keycloakmigration.changeControl.actions.role.DeleteRoleAction
import de.klg71.keycloakmigration.changeControl.actions.user.AddUserAction
import de.klg71.keycloakmigration.changeControl.actions.user.AddUserAttributeAction
import de.klg71.keycloakmigration.changeControl.actions.user.AssignGroupAction
import de.klg71.keycloakmigration.changeControl.actions.user.AssignRoleAction
import de.klg71.keycloakmigration.changeControl.actions.user.DeleteUserAction
import de.klg71.keycloakmigration.changeControl.actions.user.DeleteUserAttributeAction
import de.klg71.keycloakmigration.changeControl.actions.user.RevokeGroupAction
import de.klg71.keycloakmigration.changeControl.actions.user.RevokeRoleAction
import de.klg71.keycloakmigration.changeControl.actions.user.UpdateUserAction
import de.klg71.keycloakmigration.changeControl.actions.user.UpdateUserPasswordAction
import de.klg71.keycloakmigration.changeControl.actions.userfederation.AddAdLdapAction
import de.klg71.keycloakmigration.changeControl.actions.userfederation.DeleteUserFederationAction
import de.klg71.keycloakmigration.changeControl.actions.userfederation.mapper.AddAdLdapFullNameMapperAction
import de.klg71.keycloakmigration.changeControl.actions.userfederation.mapper.AddAdLdapGroupMapperAction
import de.klg71.keycloakmigration.changeControl.actions.userfederation.mapper.AddAdLdapHardcodedRoleMapperAction
import de.klg71.keycloakmigration.changeControl.actions.userfederation.mapper.AddAdLdapMapperAction
import de.klg71.keycloakmigration.changeControl.actions.userfederation.mapper.AddAdLdapUserAccountControlMapperAction
import de.klg71.keycloakmigration.changeControl.actions.userfederation.mapper.AddAdLdapUserAttributeMapperAction

class ActionFactory(private val objectMapper: ObjectMapper) {

    internal fun createAction(actionName: String, actionJson: String): Action =
            mapToAction(actionName, actionJson)
                    .apply {
                        yamlNodeValue = actionJson
                    }

    @Suppress("ComplexMethod")
    private fun mapToAction(actionName: String, actionJson: String): Action =
            when (actionName) {
                "addUser" -> objectMapper.readValue<AddUserAction>(actionJson)
                "updateUser" -> objectMapper.readValue<UpdateUserAction>(actionJson)
                "updateUserPassword" -> objectMapper.readValue<UpdateUserPasswordAction>(actionJson)
                "deleteUser" -> objectMapper.readValue<DeleteUserAction>(actionJson)
                "addUserAttribute" -> objectMapper.readValue<AddUserAttributeAction>(actionJson)
                "deleteUserAttribute" -> objectMapper.readValue<DeleteUserAttributeAction>(actionJson)
                "assignGroup" -> objectMapper.readValue<AssignGroupAction>(actionJson)
                "revokeGroup" -> objectMapper.readValue<RevokeGroupAction>(actionJson)

                "assignRole" -> objectMapper.readValue<AssignRoleAction>(actionJson)
                "revokeRole" -> objectMapper.readValue<RevokeRoleAction>(actionJson)

                "addRole" -> objectMapper.readValue<AddRoleAction>(actionJson)
                "deleteRole" -> objectMapper.readValue<DeleteRoleAction>(actionJson)

                "addSimpleClient" -> objectMapper.readValue<AddSimpleClientAction>(actionJson)
                "importClient" -> objectMapper.readValue<ImportClientAction>(actionJson)
                "updateClient" -> objectMapper.readValue<UpdateClientAction>(actionJson)
                "deleteClient" -> objectMapper.readValue<DeleteClientAction>(actionJson)
                "assignRoleToClient" -> objectMapper.readValue<AssignRoleToClientAction>(actionJson)

                "addClientScope" -> objectMapper.readValue<AddClientScopeAction>(actionJson)
                "assignDefaultClientScope" -> objectMapper.readValue<AssignDefaultClientScopeAction>(actionJson)

                "addGroup" -> objectMapper.readValue<AddGroupAction>(actionJson)
                "deleteGroup" -> objectMapper.readValue<DeleteGroupAction>(actionJson)
                "updateGroup" -> objectMapper.readValue<UpdateGroupAction>(actionJson)
                "assignRoleToGroup" -> objectMapper.readValue<AssignRoleToGroupAction>(actionJson)
                "revokeRoleFromGroup" -> objectMapper.readValue<RevokeRoleFromGroupAction>(actionJson)

                "addMapper" -> objectMapper.readValue<AddMapperAction>(actionJson)
                "addAudienceMapper" -> objectMapper.readValue<AddAudienceMapperAction>(actionJson)
                "addGroupMembershipMapper" -> objectMapper.readValue<AddGroupMembershipMapperAction>(actionJson)
                "addUserAttributeMapper" -> objectMapper.readValue<AddUserAttributeMapperAction>(actionJson)
                "addUserRealmRoleMapper" -> objectMapper.readValue<AddUserRealmRoleMapperAction>(actionJson)

                "addAdLdap" -> objectMapper.readValue<AddAdLdapAction>(actionJson)
                "deleteUserFederation" -> objectMapper.readValue<DeleteUserFederationAction>(actionJson)
                "addAdLdapFullNameMapper" -> objectMapper.readValue<AddAdLdapFullNameMapperAction>(actionJson)
                "addAdLdapGroupMapper" -> objectMapper.readValue<AddAdLdapGroupMapperAction>(actionJson)
                "addAdLdapHardcodedRoleMapper" -> objectMapper.readValue<AddAdLdapHardcodedRoleMapperAction>(actionJson)
                "addAdLdapMapper" -> objectMapper.readValue<AddAdLdapMapperAction>(actionJson)
                "addAdLdapUserAccountControlMapper" -> objectMapper.readValue<AddAdLdapUserAccountControlMapperAction>(
                        actionJson)
                "addAdLdapUserAttributeMapper" -> objectMapper.readValue<AddAdLdapUserAttributeMapperAction>(actionJson)

                "addRealm" -> objectMapper.readValue<AddRealmAction>(actionJson)
                "deleteRealm" -> objectMapper.readValue<DeleteRealmAction>(actionJson)
                "updateRealm" -> objectMapper.readValue<UpdateRealmAction>(actionJson)

                else -> throw ParseException(
                        "Unknown Change type: $actionName")
            }
}
