/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.service;

import az.pashabank.apl.ms.thy.model.CreateNewCardOrderResponse;
import az.pashabank.apl.ms.thy.model.CreateNewCardOrderStep1Request;
import az.pashabank.apl.ms.thy.model.CreateNewCardOrderStep2Request;
import az.pashabank.apl.ms.thy.model.CreateNewCardOrderStep3Request;
import az.pashabank.apl.ms.thy.model.CreateNewCustomerOrderResponse;
import az.pashabank.apl.ms.thy.model.GetAnnualIncomeValuesResponse;
import az.pashabank.apl.ms.thy.model.GetPromoCodeRequest;
import az.pashabank.apl.ms.thy.model.GetPromoCodeResponse;
import az.pashabank.apl.ms.thy.model.OperationResponse;
import az.pashabank.apl.ms.thy.model.UploadFilesRequest;

public interface ICardStepsService {

    OperationResponse<CreateNewCardOrderResponse> createNewCardOrderStep1(CreateNewCardOrderStep1Request request, String lang);
    OperationResponse createNewCardOrderStep2(CreateNewCardOrderStep2Request request, String lang);
    OperationResponse<CreateNewCustomerOrderResponse> createNewCardOrderStep3(CreateNewCardOrderStep3Request request, String lang);
    OperationResponse uploadFiles(UploadFilesRequest request, String lang);
    OperationResponse<GetPromoCodeResponse> getPromoCode(GetPromoCodeRequest request, String lang);
    OperationResponse<GetAnnualIncomeValuesResponse> getAnnualIncomeValues(String lang);
}
