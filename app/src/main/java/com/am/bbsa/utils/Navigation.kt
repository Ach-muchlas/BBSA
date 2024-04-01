package com.am.bbsa.utils

import android.os.Bundle
import androidx.navigation.NavController
import com.am.bbsa.R

object Navigation {
    fun navigationFragment(
        destination: Destination,
        navController: NavController,
        args: Bundle? = null
    ) {
        navController.let {
            when (destination) {
                /*Menu nasabah*/
                Destination.HOME_TO_DEPOSIT_WASTE -> it.navigate(R.id.action_navigation_home_to_wasteDepositFragment)
                Destination.HOME_TO_HISTORY_DEPOSIT -> it.navigate(R.id.action_navigation_home_to_historyDepositWasteFragment)
                Destination.HOME_TO_TYPE_WASTE -> it.navigate(R.id.action_navigation_home_to_typeWasteFragment)
                Destination.HOME_TO_PICKUP_WASTE -> it.navigate(R.id.action_navigation_home_to_pickUpWasteFragment)
                Destination.HOME_T0_WITHDRAW_BALANCE -> it.navigate(R.id.action_navigation_home_to_withdrawBalanceFragment)

                /*Menu admin*/
                Destination.MENU_TO_WASTE_TYPE_INFORMATION -> it.navigate(R.id.action_navigation_admin_menu_to_wasteTypeInformationFragment)
                Destination.MENU_TO_NASABAH -> it.navigate(R.id.action_navigation_admin_menu_to_nasabahFragment)
                Destination.MENU_TO_WASTE_DEPOSIT_ADMIN -> it.navigate(R.id.action_navigation_admin_menu_to_wasteDepositAdminFragment)
                Destination.MENU_TO_DEPOSIT_WEIGHING -> it.navigate(R.id.action_navigation_admin_menu_to_depositWeighing)
                Destination.MENU_TO_HISTORY_DEPOSIT -> it.navigate(R.id.action_navigation_admin_menu_to_historyDepositAdminFragment)
                Destination.MENU_TO_NEWS -> it.navigate(R.id.action_navigation_admin_menu_to_newsFragment)

                Destination.NASABAH_TO_DETAIL_NASABAH -> it.navigate(
                    R.id.action_nasabahFragment_to_detailNasabahFragment,
                    args
                )

                Destination.WASTE_TYPE_INFORMATION_TO_ADD_WASTE_TYPE -> it.navigate(R.id.action_wasteTypeInformationFragment_to_addWasteTypeFragment)
                Destination.WASTE_TYPE_INFORMATION_TO_UPDATE_WASTE_TYPE -> it.navigate(
                    R.id.action_wasteTypeInformationFragment_to_updateWasteTypeFragment,
                    args
                )

                Destination.DEPOSIT_WEIGHING_TO_DETAIL_DEPOSIT_WEIGHING -> it.navigate(
                    R.id.action_depositWeighing_to_detailDepositWeighingFragment,
                    args
                )

                Destination.NEWS_TO_DETAIL_NEWS -> it.navigate(
                    R.id.action_newsFragment_to_detailNewsFragment,
                    args
                )

                Destination.NEWS_TO_ADD_NEWS -> it.navigate(R.id.action_newsFragment_to_addNewsFragment)
                Destination.DETAIL_NEWS_TO_UPDATE_NEWS -> it.navigate(
                    R.id.action_detailNewsFragment_to_updateNewsFragment,
                    args
                )

                Destination.ACCOUNT_ADMIN_TO_PROFILE_ADMIN -> it.navigate(R.id.action_navigation_admin_account_to_profileAdminFragment)
                Destination.ACCOUNT_ADMIN_TO_CHANGE_LANGUAGE -> it.navigate(R.id.action_navigation_admin_account_to_changeLanguageFragment)
                Destination.ACCOUNT_ADMIN_TO_CHANGE_PASSWORD -> it.navigate(R.id.action_navigation_admin_account_to_changePasswordFragment)

            }
        }
    }
}

enum class Destination {
    /*nasabah*/
    HOME_TO_DEPOSIT_WASTE,
    HOME_T0_WITHDRAW_BALANCE,
    HOME_TO_TYPE_WASTE,
    HOME_TO_HISTORY_DEPOSIT,
    HOME_TO_PICKUP_WASTE,

    /*Admin*/
    MENU_TO_WASTE_TYPE_INFORMATION,
    MENU_TO_NASABAH,
    MENU_TO_WASTE_DEPOSIT_ADMIN,
    MENU_TO_DEPOSIT_WEIGHING,
    MENU_TO_HISTORY_DEPOSIT,
    MENU_TO_NEWS,
    NASABAH_TO_DETAIL_NASABAH,
    WASTE_TYPE_INFORMATION_TO_UPDATE_WASTE_TYPE,
    WASTE_TYPE_INFORMATION_TO_ADD_WASTE_TYPE,
    DEPOSIT_WEIGHING_TO_DETAIL_DEPOSIT_WEIGHING,
    NEWS_TO_DETAIL_NEWS,
    NEWS_TO_ADD_NEWS,
    DETAIL_NEWS_TO_UPDATE_NEWS,
    ACCOUNT_ADMIN_TO_PROFILE_ADMIN,
    ACCOUNT_ADMIN_TO_CHANGE_LANGUAGE,
    ACCOUNT_ADMIN_TO_CHANGE_PASSWORD,

}