/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.cabifymarketplace.data

import com.android.cabifymarketplace.model.Discount
import com.android.cabifymarketplace.ui.utils.Constants
import com.android.cabifymarketplace.ui.utils.Constants.Companion.DISCOUNT_RULE_MORE_THAN
import com.android.cabifymarketplace.ui.utils.Constants.Companion.DISCOUNT_RULE_QUANTITY_X_QUANTITY
import com.android.cabifymarketplace.ui.utils.Constants.Companion.DISCOUNT_TYPE_LESS_PRICE_EACH_ITEM
import com.android.cabifymarketplace.ui.utils.Constants.Companion.DISCOUNT_TYPE_PERCENTAGE
import java.math.BigDecimal

object DataSource {
    val discounts = listOf(
        Discount(
            "Compra al por mayor",
            listOf("TSHIRT"),
            Pair(DISCOUNT_RULE_MORE_THAN, 2),
            DISCOUNT_TYPE_LESS_PRICE_EACH_ITEM,
            BigDecimal(1.0),
            3
        ),
        Discount(
            "2 X 1",
            listOf("VOUCHER"),
            Pair(DISCOUNT_RULE_QUANTITY_X_QUANTITY, 2),
            DISCOUNT_TYPE_PERCENTAGE,
            BigDecimal(0.5),
            3
        )
    )

}