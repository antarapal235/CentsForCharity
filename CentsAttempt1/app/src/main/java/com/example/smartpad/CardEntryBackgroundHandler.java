package com.example.smartpad;

import android.content.res.Resources;

import sqip.Call;
import sqip.CardDetails;
import sqip.CardEntryActivityCommand;
import sqip.CardNonceBackgroundHandler;

public class CardEntryBackgroundHandler implements CardNonceBackgroundHandler {

  private final com.example.smartpad.ChargeCall.Factory chargeCallFactory;
  private final Resources resources;

  public CardEntryBackgroundHandler(com.example.smartpad.ChargeCall.Factory chargeCallFactory,
                                    Resources resources) {
    this.chargeCallFactory = chargeCallFactory;
    this.resources = resources;
  }

  @Override
  public CardEntryActivityCommand handleEnteredCardInBackground(CardDetails cardDetails) {
    if (!ConfigHelper.serverHostSet()) {
      ConfigHelper.printCurlCommand(cardDetails.getNonce());
      return new CardEntryActivityCommand.Finish();
    }

    Call<com.example.smartpad.ChargeResult> chargeCall = chargeCallFactory.create(cardDetails.getNonce());
    com.example.smartpad.ChargeResult chargeResult = chargeCall.execute();

    if (chargeResult.success) {
      return new CardEntryActivityCommand.Finish();
    } else if (chargeResult.networkError) {
      return new CardEntryActivityCommand.ShowError(resources.getString(R.string.network_failure));
    } else {
      return new CardEntryActivityCommand.ShowError(chargeResult.errorMessage);
    }
  }
}
