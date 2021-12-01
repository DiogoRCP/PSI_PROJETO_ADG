<?php

namespace frontend\modules\api\controllers;
use backend\models\User;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;
class CompanieslistController extends ActiveController
{
    public $modelClass = 'backend\models\Companies';

    /*public function behaviors()
    {
        $behaviors = parent::behaviors();
        $behaviors['authenticator'] = [
            'class' => QueryParamAuth::className()
        ];

        return $behaviors;
    }
    public function auth($token) {

        $user = User::findIdentityByAccessToken($token);
        if ($user !=null)
        {
            return $user;
        } return null;
    }
    */

    public function actionTotal(){
        $Companiessmodel = new $this -> modelClass;
        $recs = $Companiessmodel::find() -> all();
        return ['total' => count($recs)];
    }


// http://localhost:8080/v1/companies/post

    public function actionPost() {

}

    //http://localhost:8080/v1/companies/delete/id

    public function actionDelete($id)
    {
    }
}
