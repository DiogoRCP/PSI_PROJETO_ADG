<?php

$params = require __DIR__ . '/params.php';
$db = require __DIR__ . '/db.php';

$config = [
    'id' => 'basic',
    'basePath' => dirname(__DIR__),
    'bootstrap' => ['log'],
    'aliases' => [
        '@bower' => '@vendor/bower-asset',
        '@npm'   => '@vendor/npm-asset',
    ],
    'modules' => [
        'v1' => [
            'class' => 'app\modules\v1\Module',
        ],
    ],
    'components' => [
        'request' => [
            // !!! insert a secret key in the following (if it is empty) - this is required by cookie validation
            'cookieValidationKey' => 'giEnxSi5rNOL1Nwz0F2b5djyDSZMGvEj',
        ],
        'cache' => [
            'class' => 'yii\caching\FileCache',
        ],
        'user' => [
            'identityClass' => 'app\models\User',
            'enableAutoLogin' => true,
        ],
        'errorHandler' => [
            'errorAction' => 'site/error',
        ],
        'mailer' => [
            'class' => 'yii\swiftmailer\Mailer',
            // send all mails to a file by default. You have to set
            // 'useFileTransport' to false and configure a transport
            // for the mailer to send real emails.
            'useFileTransport' => true,
        ],
        'log' => [
            'traceLevel' => YII_DEBUG ? 3 : 0,
            'targets' => [
                [
                    'class' => 'yii\log\FileTarget',
                    'levels' => ['error', 'warning'],
                ],
            ],
        ],
        'db' => $db,
/*comentar aqui se for necessário aceder ao gii*/
        'urlManager' => [
            'enablePrettyUrl' => true,
            'showScriptName' => false,
            'rules' => [
                ['class' => 'yii\rest\UrlRule',
                    'controller' => 'v1/user',
                    'pluralize' => false,
                    'extraPatterns' => [
                        'GET total' => 'total' ,
                        'GET set/{limit}' => 'set',
                        'POST post' => 'post',
                        'PUT put/{id}'=>'put',
                        'DELETE  delete/{id}' => 'delete'
                    ],
                    'tokens' => [ '{id}'    => '<id:\d+>', '{limit}' => '<limit:\d+>', ],

                    'controller' => 'v1/repairs',
                    'pluralize' => false,
                    'extraPatterns' => [
                        'GET total' => 'total' ,
                        'GET set/{limit}' => 'set',
                        'POST post' => 'post',
                        'PUT put/{id}'=>'put',
                        'DELETE  delete/{id}' => 'delete'
                    ],
                    'tokens' => [ '{id}'    => '<id:\d+>', '{limit}' => '<limit:\d+>', ],

                    'controller' => 'v1/companies',
                    'pluralize' => false,
                    'extraPatterns' => [
                        'GET total' => 'total' ,
                        'GET set/{limit}' => 'set',
                        'POST post' => 'post',
                        'PUT put/{id}'=>'put',
                        'DELETE  delete/{id}' => 'delete'
                    ],
                    'tokens' => [ '{id}'    => '<id:\d+>', '{limit}' => '<limit:\d+>', ],

                    'controller' => 'v1/cars',
                    'pluralize' => false,
                    'extraPatterns' => [
                        'GET total' => 'total' ,
                        'GET set/{limit}' => 'set',
                        'POST post' => 'post',
                        'PUT put/{id}'=>'put',
                        'DELETE  delete/{id}' => 'delete'
                    ],
                    'tokens' => [ '{id}'    => '<id:\d+>', '{limit}' => '<limit:\d+>', ],

                    'controller' => 'v1/contributors',
                    'pluralize' => false,
                    'extraPatterns' => [
                        'GET total' => 'total' ,
                        'GET set/{limit}' => 'set',
                        'POST post' => 'post',
                        'PUT put/{id}'=>'put',
                        'DELETE  delete/{id}' => 'delete'
                    ],
                    'tokens' => [ '{id}'    => '<id:\d+>', '{limit}' => '<limit:\d+>', ],
                ]
            ],
        ],

    ],
    'params' => $params,
];

if (YII_ENV_DEV) {
    // configuration adjustments for 'dev' environment
    $config['bootstrap'][] = 'debug';
    $config['modules']['debug'] = [
        'class' => 'yii\debug\Module',
        // uncomment the following to add your IP if you are not connecting from localhost.
        //'allowedIPs' => ['127.0.0.1', '::1'],
    ];

    $config['bootstrap'][] = 'gii';
    $config['modules']['gii'] = [
        'class' => 'yii\gii\Module',
        // uncomment the following to add your IP if you are not connecting from localhost.
        //'allowedIPs' => ['127.0.0.1', '::1'],
    ];
}

return $config;