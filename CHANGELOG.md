HiPay Android SDK change log and release notes
================================================

Unreleased - 2.1.1
-----

* Fixed `language` parameter on OrderRequest

2.1.0
-----

* Target API 33
* ⚠️ Drop support Android SDK version (minSdkVersion = 23)

2.0.0
-----

* Remove `com.github.devnied.emvnfccard` library

1.7.0
-----

* ⚠️ Drop support Android SDK version (minSdkVersion = 21)
* Target API 31
* Fix Overflow placeholder in TextInput

1.6.4
-----

* Add Italian
* Remove pop-up in PaymentCardsActivity

1.6.3
-----

* Add updatePaymentCard function

1.6.2
-----

* Fix updatePaymentCard function

1.6.1
-----

* Fix Transaction State in ForwardWebViewActivity if "Feedback Parameters" disabled

1.6.0
-----

* minSdkVersion 14 -> 16
* Migrating to Androidx
* Fix DSP2 : Crash when customer name and shipping name are empty
* Domestic network has priority over brand network
* Add new type of error "technical_error"
* Add new strings for alert in strings.xml

1.5.1
-----

* Fix deployement on maven.org

1.5.0
-----

* Add DSP2

1.4.1
-----

* Fix Secure Vault V2

1.4.0
-----

* Add Secure Vault V2

1.3.7
-----

* Add maven script for deployment.

1.3.6
-----

* Handle Bancontact scheme app.

1.3.5
-----

* Fixes the wrong operation parameter.

1.3.4
-----

* Fixes the cancel loader requests.

1.3.3
-----

* Fixes the available-payment-products url in production.

1.3.2
-----

* Fixes the bug related to space in card number form.

1.3.1
-----

* Add more logging to exceptions.

1.2.1
-----

* Display the scan card button if device supports card scanning.

1.2.0
-----

* Add the scan card camera feature.
* Add the NFC scan card feature.

1.1.1
-----

* Fixes the bug related to the tokenizable creation.

1.1.0
-----

* Add the 1-click payment feature.

1.0.0
-----

* First major version of the HiPay Enterprise Android SDK.
