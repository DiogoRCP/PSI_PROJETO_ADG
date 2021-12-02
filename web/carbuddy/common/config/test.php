<?php
return [
    'id' => 'app-common-tests',
    'basePath' => dirname(__DIR__),
    'components' => [
        'db' => [
            'dsn' => 'mysql:host=localhost;dbname=carbuddy',
        ],
        'user' => [
            'class' => 'yii\web\User',
            'identityClass' => 'common\models\User',
        ],
    ],
];
