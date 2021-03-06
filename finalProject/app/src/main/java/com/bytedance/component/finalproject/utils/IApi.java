package com.bytedance.component.finalproject.utils;

import com.bytedance.component.finalproject.net.GetResponse;
import com.bytedance.component.finalproject.net.UploadResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IApi {

    @Multipart
    @POST("video")
    Call<UploadResponse> submitVideo(@Query("student_id") String studentId,
                                     @Query("user_name") String userName,
                                     @Query("extra_value") String extraValue,
                                     @Part MultipartBody.Part coverImage,
                                     @Part MultipartBody.Part video);


    @GET("video")
    Call<GetResponse> getVideo();

}
