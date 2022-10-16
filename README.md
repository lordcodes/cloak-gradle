<p align="center">
    <img src="art/logo.png" width="500" max-width="90%" alt="Cloak Swift" />
</p>

<p align="center">
     <a href="https://github.com/lordcodes/cloak-gradle/releases/latest">
         <img src="https://img.shields.io/github/release/lordcodes/cloak-gradle.svg?style=flat" alt="Latest release" />
     </a>
    <a href="https://twitter.com/lordcodes">
        <img src="https://img.shields.io/badge/twitter-@lordcodes-blue.svg?style=flat" alt="Twitter: @lordcodes" />
    </a>
</p>

---

This is **Cloak Gradle** - a Gradle plugin to encrypt secrets and then pass them in an obfuscated form into Android apps.

&nbsp;

<p align="center">
    <a href="#features">Features</a> • <a href="#install">Install</a> • <a href="#usage">Usage</a> • <a href="#contributing-or-help">Contributing</a>
</p>

## Features

#### ☑️ Keep your secrets out of Git

Set up secrets locally, keep them out of Git and avoid them being embedded into the code.

#### ☑️ Encrypt secrets

Create encryption key and encrypt secrets ready for use.

#### ☑️ Access secrets from your app

Expose secrets to app source code via a generated Kotlin file or the `AndroidManifest`.

#### ☑️ Obfuscation

Include the secret values in an obfuscated form rather than simply as plain text.

&nbsp;

## Install

Cloak Gradle is provided as a plugin available from the Gradle Plugin portal.

### ▶︎ Using version catalog

1. In `gradle/libs.versions.toml`:

```toml
[versions]
cloakGradlePlugin = "VERSION"

[plugins]
cloak = { id = "com.lordcodes.cloak", version.ref = "cloakGradlePlugin" }
```

2. At project root:

Kotlin `build.gradle.kts`:

```kotlin
plugins {
    alias(libs.plugins.cloak) apply false
}
```

Groovy `build.gradle`:

```groovy
plugins {
    alias(libs.plugins.cloak) apply false
}
```

&nbsp;

## Usage

### Set up configuration

Create a configuration file within your project: `.cloak/config`, this file should be kept in Git and shared between contributors. Enter key-value pairs into the file [EnvironmentKey](Sources/CloakKit/Configuration/EnvironmentKey.swift).

* CLOAK_SECRETS_CLASS_NAME -> Name to give the generated Swift enum that contains the secrets in-app.
* CLOAK_SECRETS_OUTPUT_FILEPATH -> File path to put the generated Swift file.
* CLOAK_SECRETS_ACCESS_LEVEL -> Swift access level to give to the enum and each secret static property. E.g. public.

Each of these settings can be provided as an environment variable instead of listed in the configuration file. The config file will take precedance.

For example:

```
CLOAK_SECRETS_CLASS_NAME=AppSecrets
CLOAK_SECRETS_OUTPUT_FILEPATH=Sources/Generated/AppSecrets.swift
CLOAK_SECRETS_ACCESS_LEVEL=public
```

### Configure required secret keys

You can list the required secret keys for your project in a `.cloak/secret-keys` file, which can be kept in Git. This ensures each contributor has provided all required secrets locally. Secret keys should be listed one on each line.

For example:

```
ANALYTICS_WRITE_KEY
API_CLIENT_ID
API_CLIENT_SECRET
```

### Configure secrets

Each contributor on a project will need to create a file at `.cloak/secrets` that uses the same format as the `config` file but that lists secret key names and values. This file should be added to your project's `.gitignore` to keep them out of Git.

You should also add your encryption key to this file using the key name `CLOAK_ENCRYPTION_KEY`. This will allow the encrypt/decrypt commands to function and will also allow it to be included into the generated Swift file so that your app can decrypt the secrets at runtime in order to use them.

If the secret keys are specified in the required keys file `secret-keys`, then they will be read as environment variables as well, where the environment variables take precendence. This is useful in a CI environment where you can specify them as environment variables and avoid having to write them to a file as you would locally.

IMPORTANT NOTE: The secrets aren't read as environment variables correctly when using Cloak as a Tuist plugin, due to the environment Tuist plugins are executed in. Therefore, it is best to write the secrets to a file in a setup step of your CI workflow.

The best practice is that the values should be encrypted first.

#### Create encryption key

Generates an encryption key, that can then be used within your project to encrypt secrets. This key is then passed into your app so that you can decrypt them at runtime.

`./gradlew cloak createkey`

#### Encrypt a value

Provide a value and the encrypted version will be returned. Your encryption key should be provided as described above.

`./gradlew cloak encrypt <value>`

#### Decrypt an encrypted value

Provide an encrypted value and the decrypted version will be returned. Your encryption key should be provided as described above.

`./gradlew cloak decrypt <encrypted>`

#### Generate a secrets file in-app

Generate a Swift file that can be used to access your secrets within your app at runtime. Certain aspects of the generated file can be customised using the `config` file as described above. The secrets will be obfuscated and included as `[UInt8]`, but with Swift properties to return them as `String` in their usable form.

`./gradlew cloak generate`

## Contributing or Help

If you notice any bugs or have a new feature to suggest, please check out the [contributing guide](https://github.com/lordcodes/cloak-gradle/blob/master/CONTRIBUTING.md). If you want to make changes, please make sure to discuss anything big before putting in the effort of creating the PR.

To reach out, please contact [@lordcodes on Twitter](https://twitter.com/lordcodes).
