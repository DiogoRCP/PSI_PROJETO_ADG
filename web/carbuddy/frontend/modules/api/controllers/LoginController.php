<?php

namespace frontend\modules\api\controllers;

use Cassandra\Exception\ValidationException;
use Yii;
use yii\base\UserException;
use yii\db\Exception;
use yii\helpers\VarDumper;
use yii\rest\ActiveController;
use common\mosquitto\phpMQTT;
use yii\web\ForbiddenHttpException;
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
