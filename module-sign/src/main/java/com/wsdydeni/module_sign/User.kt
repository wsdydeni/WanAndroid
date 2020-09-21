package com.wsdydeni.module_sign

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
data class User(
    val admin: Boolean,
    val chapterTops: ArrayList<String>?,
    val coinCount: Int,
    val collectIds: ArrayList<Int>,
    val email: String?,
    val icon: String?,
    val id: Int,
    val nickname: String?,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String) : Parcelable {

    override fun equals(other: Any?): Boolean = if(other is User) this.id == other.id else false

    override fun hashCode(): Int {
        var result = admin.hashCode()
        result = 31 * result + chapterTops.hashCode()
        result = 31 * result + coinCount
        result = 31 * result + collectIds.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + icon.hashCode()
        result = 31 * result + id
        result = 31 * result + nickname.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + publicName.hashCode()
        result = 31 * result + token.hashCode()
        result = 31 * result + type
        result = 31 * result + username.hashCode()
        return result
    }


}