package com.status.callie.services;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by jivan.ghadage on 8/29/2016.
 */
public class ErrorUtils {
    public static ApiError parseError(Response<?> response) throws IOException {
        Converter<ResponseBody, ApiError> converter =
                ApiClient.getClient()
                        .responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ApiError();
        }

        return error;
    }
}
