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
                Destination.HOME_TO_DETAIL_BALANCE -> it.navigate(R.id.action_navigation_home_to_detailBalanceFragment)
                Destination.HOME_TO_DETAIL_NEWS -> it.navigate(
                    R.id.action_navigation_home_to_detailNewsNasabahFragment,
                    args
                )
                /*Menu admin*/
                Destination.MENU_TO_WASTE_TYPE_INFORMATION -> it.navigate(R.id.action_navigation_admin_menu_to_wasteTypeInformationFragment)
                Destination.MENU_TO_NASABAH -> it.navigate(R.id.action_navigation_admin_menu_to_nasabahFragment)
                Destination.MENU_TO_WASTE_DEPOSIT_ADMIN -> it.navigate(R.id.action_navigation_admin_menu_to_wasteDepositAdminFragment)
                Destination.MENU_TO_DEPOSIT_WEIGHING -> it.navigate(R.id.action_navigation_admin_menu_to_depositWeighing)
                Destination.MENU_TO_HISTORY_DEPOSIT -> it.navigate(R.id.action_navigation_admin_menu_to_historyDepositAdminFragment)
                Destination.MENU_TO_NEWS -> it.navigate(R.id.action_navigation_admin_menu_to_newsFragment)
                Destination.MENU_TO_UPDATE_PRICE -> it.navigate(R.id.action_navigation_admin_menu_to_update_price_waste)
                Destination.MENU_TO_HISTORY_WITHDRAW_BALANCE -> it.navigate(R.id.action_navigation_admin_menu_to_historyWithdrawBalanceAdminFragment)
                Destination.MENU_TO_REPORT_FRAGMENT -> it.navigate(R.id.action_navigation_admin_menu_to_reportFragment)

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

                Destination.ACCOUNT_TO_PROFILE -> it.navigate(R.id.action_navigation_account_to_profileFragment)
                Destination.ACCOUNT_TO_CHANGE_PASSWORD -> it.navigate(R.id.action_navigation_account_to_changePasswordAdminFragment)
                Destination.PROFILE_TO_UPDATE_PROFILE -> it.navigate(
                    R.id.action_profileFragment_to_updateProfileFragment,
                    args
                )


                Destination.ACCOUNT_ADMIN_TO_PROFILE_ADMIN -> it.navigate(R.id.action_navigation_admin_account_to_profileAdminFragment)
                Destination.ACCOUNT_ADMIN_TO_ADD_ADMIN -> it.navigate(R.id.action_navigation_admin_account_to_addAdminFragment)
                Destination.ACCOUNT_ADMIN_TO_CHANGE_PASSWORD -> it.navigate(R.id.action_navigation_admin_account_to_changePasswordFragment)

                Destination.MENU_TO_SCHEDULING_PICK_UP -> it.navigate(R.id.action_navigation_admin_menu_to_schedulingWastePickUpFragment)
                Destination.SCHEDULING_PICK_UP_TO_ADD_SCHEDULE -> it.navigate(R.id.action_schedulingWastePickUpFragment_to_addSchedulingWastePickUpFragment)

                Destination.DETAIL_NASABAH_TO_UPDATE_NASABAH -> it.navigate(
                    R.id.action_detailNasabahFragment_to_updateNasabahFragment,
                    args
                )

                Destination.DETAIL_NASABAH_TO_UPDATE_PHOTO_PROFILE -> it.navigate(
                    R.id.action_detailNasabahFragment_to_updatePhotoProfileFragment,
                    args
                )

                Destination.HISTORY_DEPOSIT_TO_DETAIL_HISTORY_DEPOSIT -> it.navigate(
                    R.id.action_historyDepositAdminFragment_to_detailHistoryDepositFragment,
                    args
                )

                Destination.HISTORY_DEPOSIT_WASTE_TO_DETAIL_HISTORY_DEPOSIT -> it.navigate(
                    R.id.action_historyDepositWasteFragment_to_detailHistoryDepositFragment,
                    args
                )

                Destination.PICK_UP_WASTE_TO_REGISTER_PICK_UP_WASTE -> it.navigate(
                    R.id.action_pickUpWasteFragment_to_registerWastePickUpFragment,
                    args
                )

                Destination.SCHEDULE_PICK_UP_WASTE_TO_DETAIL_SCHEDULE_PICK_UP_WASTE -> it.navigate(
                    R.id.action_schedulingWastePickUpFragment_to_detailSchedulePickupWasteFragment,
                    args
                )

                Destination.DETAIL_BALANCE_TO_DETAIL_HISTORY_WITHDRAW_BALANCE -> it.navigate(
                    R.id.action_detailBalanceFragment_to_detailHistoryWithdrawBalanceFragment,
                    args
                )

                Destination.HISTORY_WITHDRAW_BALANCE_ADMIN_TO_DETAIL_HISTORY_WITHDRAW_BALANCE -> it.navigate(
                    R.id.action_historyWithdrawBalanceAdminFragment_to_detailHistoryWithdrawBalanceFragment,
                    args
                )

                Destination.PROFILE_TO_UPDATE_PHOTO_PROFILE -> it.navigate(
                    R.id.action_profileFragment_to_updatePhotoProfileFragment,
                    args
                )

                Destination.PROFILE_ADMIN_T0_UPDATE_PHOTO_PROFILE -> it.navigate(
                    R.id.action_profileAdminFragment_to_updatePhotoProfileFragment,
                    args
                )

                Destination.PROFILE_ADMIN_TO_UPDATE_PROFILE -> it.navigate(
                    R.id.action_profileAdminFragment_to_updateProfileFragment,
                    args
                )
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
    HOME_TO_DETAIL_BALANCE,
    HOME_TO_DETAIL_NEWS,
    ACCOUNT_TO_PROFILE,
    ACCOUNT_TO_CHANGE_PASSWORD,
    PROFILE_TO_UPDATE_PROFILE,
    PROFILE_TO_UPDATE_PHOTO_PROFILE,
    HISTORY_DEPOSIT_WASTE_TO_DETAIL_HISTORY_DEPOSIT,
    PICK_UP_WASTE_TO_REGISTER_PICK_UP_WASTE,
    DETAIL_BALANCE_TO_DETAIL_HISTORY_WITHDRAW_BALANCE,
    HISTORY_WITHDRAW_BALANCE_ADMIN_TO_DETAIL_HISTORY_WITHDRAW_BALANCE,

    /*Admin*/
    MENU_TO_WASTE_TYPE_INFORMATION,
    MENU_TO_NASABAH,
    MENU_TO_WASTE_DEPOSIT_ADMIN,
    MENU_TO_DEPOSIT_WEIGHING,
    MENU_TO_HISTORY_DEPOSIT,
    MENU_TO_NEWS,
    MENU_TO_UPDATE_PRICE,
    MENU_TO_SCHEDULING_PICK_UP,
    MENU_TO_HISTORY_WITHDRAW_BALANCE,
    MENU_TO_REPORT_FRAGMENT,
    NASABAH_TO_DETAIL_NASABAH,
    DETAIL_NASABAH_TO_UPDATE_NASABAH,
    DETAIL_NASABAH_TO_UPDATE_PHOTO_PROFILE,
    WASTE_TYPE_INFORMATION_TO_UPDATE_WASTE_TYPE,
    WASTE_TYPE_INFORMATION_TO_ADD_WASTE_TYPE,
    DEPOSIT_WEIGHING_TO_DETAIL_DEPOSIT_WEIGHING,
    NEWS_TO_DETAIL_NEWS,
    NEWS_TO_ADD_NEWS,
    DETAIL_NEWS_TO_UPDATE_NEWS,
    ACCOUNT_ADMIN_TO_PROFILE_ADMIN,
    ACCOUNT_ADMIN_TO_ADD_ADMIN,
    ACCOUNT_ADMIN_TO_CHANGE_PASSWORD,
    SCHEDULING_PICK_UP_TO_ADD_SCHEDULE,
    PROFILE_ADMIN_TO_UPDATE_PROFILE,
    PROFILE_ADMIN_T0_UPDATE_PHOTO_PROFILE,
    HISTORY_DEPOSIT_TO_DETAIL_HISTORY_DEPOSIT,
    SCHEDULE_PICK_UP_WASTE_TO_DETAIL_SCHEDULE_PICK_UP_WASTE
}