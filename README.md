# fa-vs-gma

`Firebase Ads` vs `Google Mobile Ads`

## Motivation

Currently, Google's mobile advertising SDK is published as multiple products:

- Firebase Ads (`firebase-ads`)
- Google Mobile Ads SDK (`play-services-ads)`
- Google Mobile Ads Lite SDK (`play-services-ads-lite`)

And, They use the same namespace, I/F.

This repository is test to make sure to can use the same implementation for different SDKs.

## How to build

```sh
./gradlew :core:publishToMavenLocal
# ...
# BUILD SUCCESSFUL in 9s
# 73 actionable tasks: 6 executed, 67 up-to-date
open -a 'Android Studio' .
```

You can switch Ads SDK in `Build Variants` window.
