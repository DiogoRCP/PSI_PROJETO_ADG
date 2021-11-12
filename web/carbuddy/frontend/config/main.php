<?php
$params = array_merge(
    require __DIR__ . '/../../common/config/params.php',
    require __DIR__ . '/../../common/config/params-local.php',
    require __DIR__ . '/params.php',
    require __DIR__ . '/params-local.php'
);

return [
    'id' => 'app-frontend',
    'basePath' => dirname(__DIR__),
    'bootstrap' => ['log'],
    'controllerNamespace' => 'frontend\controllers',
    'modules' => [
        'api' => [
            'class' => 'frontend\modules\api\Module',
        ],
    ],
    'components' => [
        'request' => [
            'csrfParam' => '_csrf-frontend',
        ],
        'user' => [
            'identityClass' => 'common\models\User',
            'enableAutoLogin' => true,
            'identityCookie' => ['name' => '_identity-frontend', 'httpOnly' => true],
        ],
        'session' => [
            // this is the name of the session cookie used for login on the frontend
            'name' => 'advanced-frontend',
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
        'errorHandler' => [
            'errorAction' => 'site/error',
        ],

        'urlManager' => [
            'enablePrettyUrl' => true,
            'showScriptName' => false,
            'rules' => [
                ['class' => 'yii\rest\UrlRule',

                    'controller' => 'api/signup',
                    'pluralize' => false,
                    'extraPatterns' => [
                        'POST post' => 'post',
                    ],

                    'controller' => 'api/login',
                    'pluralize' => false,
                    'extraPatterns' => [
                        'GET get/{username}/{password}' => 'get'
                    ],
                    'tokens' => ['{username}' => '<username:\d+>', '{password}' => '<password:\d+>',],

                    'controller' => 'api/user',
                    'pluralize' => false,
                    'extraPatterns' => [
                        'GET total' => 'total',
                        'GET set/{limit}' => 'set',
                        'POST post' => 'post',
                        'PUT put' => 'put',
                        'DELETE  delete' => 'delete'
                    ],
                    'tokens' => ['{limit}' => '<limit:\d+>',],

                    'controller' => 'api/repairs',
                    'pluralize' => false,
                    'extraPatterns' => [
                        'GET total' => 'total',
                        'GET set/{limit}' => 'set',
                        'POST post' => 'post',
                        'PUT put/{id}' => 'put',
                        'DELETE  delete/{id}' => 'delete'
                    ],
                    'tokens' => ['{id}' => '<id:\d+>', '{limit}' => '<limit:\d+>',],

                    'controller' => 'api/companies',
                    'pluralize' => false,
                    'extraPatterns' => [
                        'GET total' => 'total',
                        'GET set/{limit}' => 'set',
                        'POST post' => 'post',
                        'PUT put/{id}' => 'put',
                        'DELETE  delete/{id}' => 'delete'
                    ],
                    'tokens' => ['{id}' => '<id:\d+>', '{limit}' => '<limit:\d+>',],

                    'controller' => 'api/cars',
                    'pluralize' => false,
                    'extraPatterns' => [
                        'GET total' => 'total',
                        'GET totaluser' => 'totaluser',
                        'GET set/{limit}' => 'set',
                        'POST post' => 'post',
                        'PUT put/{id}' => 'put',
                        'DELETE  delete/{id}' => 'delete'
                    ],
                    'tokens' => ['{id}' => '<id:\d+>', '{limit}' => '<limit:\d+>',],

                    'controller' => 'api/contributors',
                    'pluralize' => false,
                    'extraPatterns' => [
                        'GET total' => 'total',
                        'GET set/{limit}' => 'set',
                        'POST post' => 'post',
                        'PUT put/{id}' => 'put',
                        'DELETE  delete/{id}' => 'delete'
                    ],
                    'tokens' => ['{id}' => '<id:\d+>', '{limit}' => '<limit:\d+>',],
                ]
            ],
        ],
        

    ],
    'params' => $params,
];
