<?php

namespace frontend\modules\api\controllers;

use Yii;
use yii\rest\ActiveController;
use yii\web\UnauthorizedHttpException;

class LoginController extends ActiveController
{
    public $modelClass = 'common\models\LoginForm';

    public function actionDo()
    {
        $login = ['LoginForm' => [
            'username' => Yii::$app->request->post('username'),
            'password' => Yii::$app->request->post('password')]
        ];

        $loginmodel = new $this->modelClass;

        $type = "frontend";
        if ($loginmodel->load($login) && $loginmodel->login($type)) {
            $user = Yii::$app->user->getIdentity();

            return ["user" => $user];
        }
        throw new UnauthorizedHttpException("Failed login");
    }
}
