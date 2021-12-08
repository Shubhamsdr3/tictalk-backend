package com.pandey.shubham.services

import com.google.cloud.storage.BucketInfo
import com.google.cloud.storage.StorageOptions
import com.pandey.shubham.util.Constants

class UploadService {

    private val storage = StorageOptions.getDefaultInstance().service
    private val bucket = storage.create(BucketInfo.of(Constants.BUCKET_NAME))

}