<?php

namespace frontend\modules\api\controllers;

use Yii;
use yii\helpers\VarDumper;
use yii\rest\ActiveController;
use common\mosquitto\phpMQTT;

class LoginController extends ActiveController
{
    public $modelClass = 'common\models\LoginForm';

    public function actionGet($username, $password)
    {

        $login = ['LoginForm' => [
            'username' => $username,
            'password' => $password]
        ];

        $loginmodel = new $this->modelClass;

        $type = "frontend";
        if ($loginmodel->load($login) && $loginmodel->login($type)) {
            $user = Yii::$app->user->getIdentity();

            return ["user" => $user, "repair" => $this->FazSubscribe("REPAIR-".Yii::$app->user->getId())];
        }

        return ['Login' => false];
    }

    public function FazSubscribe($canal)
    {
        $server = "127.0.0.1";
        $port = 1883;
        $mqtt = new phpMQTT($server, $port, "phpMQTT-subscriber");
        $mqtt->connect();

        $message = $mqtt->subscribeAndWaitForMessage($canal, 0);

        $mqtt->disconnect();
        return $mqtt->proc(true);
    }
}
