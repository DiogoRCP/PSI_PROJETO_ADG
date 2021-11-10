<?php

namespace frontend\modules\api\controllers;

use Yii;
use yii\filters\auth\QueryParamAuth;
use yii\helpers\VarDumper;
use yii\rest\ActiveController;

class SignupController extends ActiveController
{
    public $modelClass = 'frontend\models\SignupForm';

    public function actionPost()
    {
        $user = json_decode(Yii::$app->request->rawBody);

        $signupmodel = new $this->modelClass;

        $signupmodel->username = $user->username;
        $signupmodel->password = $user->password;
        $signupmodel->nif = $user->nif;
        $signupmodel->email = $user->email;
        $signupmodel->phonenumber = $user->phonenumber;
        $signupmodel->birsthday = $user->birsthday;

        $ret = $signupmodel->signup();
        return ['Save' => $ret];
    }

}
