<?php

namespace frontend\modules\api\controllers;

use Yii;
use yii\rest\ActiveController;

class LoginController extends ActiveController
{
    public $modelClass = 'common\models\LoginForm';

    public function actionGet($username, $password)
    {

        $login = ['LoginForm' =>[
            'username' => $username,
            'password' => $password]
        ];

        $loginmodel = new $this->modelClass;

        $type = "frontend";
        if ($loginmodel->load($login) && $loginmodel->login($type)) {
            return ['Login' => true, 'authkey' => Yii::$app->user->getIdentity()->getAuthKey()];
        }

        return ['Login' => false];
    }

}
