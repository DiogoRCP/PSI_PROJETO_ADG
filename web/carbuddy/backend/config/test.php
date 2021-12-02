<?php
return [
    'id' => 'app-backend-tests',
    'components' => [
        'db' => [
            'dsn' => 'mysql:host=localhost;dbname=carbuddy',
        ],
        'assetManager' => [
            'basePath' => __DIR__ . '/../web/assets',
        ],
        'urlManager' => [
            'showScriptName' => true,
        ],
        'request' => [
            'cookieValidationKey' => 'test',
        ],
    ],
];
