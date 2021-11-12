<?php

namespace frontend\modules\api\controllers;

use Yii;
use yii\rest\ActiveController;

class LoginController extends ActiveController
{
    public $modelClass = 'common\models\LoginForm';

    public function actionPost()
    {
        $user = json_decode(Yii::$app->request->rawBody);

        $loginmodel = new $this->modelClass;

        $type = "frontend";
        if ($loginmodel->load(Yii::$app->request->rawBody) && $loginmodel->login($type)) {
            return ['Login' => true];
        }

        return ['Login' => false];
    }

}
