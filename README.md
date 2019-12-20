This is the Android Scanner App that will be used to scan QR-Codes as part of the Take 'n Trace project.
You must be connected to the school network to be able to use the app.

LoginActivity -> HomeActivity (main menu) -> MainActivity (Scan function for delivery)
                                          -> DeliveryActivity ( List of current scanned deliveries)
                                          -> PickUpActivity (Scan for pickup)
                                          -> Dispatch (Call to dispatch number)
                                          -> DeliveredActivity (Scan for delived) -> SignatureActivity (Signature box on delivered)
Unit tests:
-Check scan works on right type of barcode
