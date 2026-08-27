package com.educalab.ceosim.data.local

import androidx.room.TypeConverter
import com.educalab.ceosim.data.local.entity.TransactionType
import com.educalab.ceosim.domain.model.ChallengeType
import com.educalab.ceosim.domain.model.CustomerAvatar
import com.educalab.ceosim.domain.model.ProductCategory
import com.educalab.ceosim.domain.model.UpgradeCategory

/**
 * Room no persiste enums directamente: los guardamos como texto (nombre del
 * enum) y estos conversores hacen la traducción en ambos sentidos.
 */
class Converters {

    @TypeConverter
    fun fromProductCategory(value: ProductCategory): String = value.name

    @TypeConverter
    fun toProductCategory(value: String): ProductCategory = ProductCategory.valueOf(value)

    @TypeConverter
    fun fromCustomerAvatar(value: CustomerAvatar): String = value.name

    @TypeConverter
    fun toCustomerAvatar(value: String): CustomerAvatar = CustomerAvatar.valueOf(value)

    @TypeConverter
    fun fromUpgradeCategory(value: UpgradeCategory): String = value.name

    @TypeConverter
    fun toUpgradeCategory(value: String): UpgradeCategory = UpgradeCategory.valueOf(value)

    @TypeConverter
    fun fromChallengeType(value: ChallengeType): String = value.name

    @TypeConverter
    fun toChallengeType(value: String): ChallengeType = ChallengeType.valueOf(value)

    @TypeConverter
    fun fromTransactionType(value: TransactionType): String = value.name

    @TypeConverter
    fun toTransactionType(value: String): TransactionType = TransactionType.valueOf(value)
}
