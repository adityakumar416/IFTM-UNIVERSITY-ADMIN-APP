package com.example.iftmadmin

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowAllComplaintModel(val complaintId:String?=null,
                                 val complaintImage:String?=null,
                                 val complaint:String?=null,) : Parcelable
