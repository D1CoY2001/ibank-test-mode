# ibank-test-mode

Автоматизированные тесты авторизации интернет-банка с использованием Selenide, Rest Assured и GitHub Actions.

## Запуск приложения

```powershell
Start-Process java -ArgumentList '-jar','artifacts/app-ibank.jar','-P:profile=test'
```

## Запуск тестов

```powershell
./gradlew test
```

## CI

[![Java CI](https://github.com/D1CoY2001/ibank-test-mode/actions/workflows/gradle.yml/badge.svg)](https://github.com/D1CoY2001/ibank-test-mode/actions/workflows/gradle.yml)