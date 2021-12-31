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
        $user = Yii::$app->request->post();

        $signupmodel = new $this->modelClass;

        $signupmodel->username = $user['username'];
        $signupmodel->password = $user['password'];
        $signupmodel->nif = $user['nif'];
        $signupmodel->email = $user['email'];
        $signupmodel->phonenumber = $user['phonenumber'];
        $signupmodel->birsthday = $user['birsthday'];

        $ret = $signupmodel->signup();
        if($ret == true) $signupmodel->AssignUser();
        echo json_encode(["message" => $ret]);
        return ['Save' => $ret];
    }

}
