package locsapp.locsapp.interfaces;

import locsapp.locsapp.models.BillingAddress;
import locsapp.locsapp.models.LivingAddress;

/**
 * Created by Damien on 3/22/2016.
 */

public interface DeleteAddress {
    void deleteLivingAddress(LivingAddress adr);
    void deleteBillingAddress(BillingAddress adr);
}
