<?php

namespace frontend\modules\api\controllers;
use backend\models\User;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;
class CompaniesController extends ActiveController
{
    public $modelClass = 'backend\models\Companies';

    public function behaviors()
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

    public function actionIndex()
    {
        return $this->render('index');
    }

    public function actionTotal(){
        $Companiessmodel = new $this -> modelClass;
        $recs = $Companiessmodel::find() -> all();
        return ['total' => count($recs)];
    }

    //http://localhost:8080/v1/companies/set/3

    public function actionSet($limit){
        $Companiessmodel = new $this -> modelClass;
        $rec = $Companiessmodel::find() -> limit($limit) -> all();
        return ['limite' => $limit, 'Records' => $rec ];
    }

// http://localhost:8080/v1/companies/post

    public function actionPost() {

        $name=\Yii::$app -> request -> post('name');

        $Companiessmodel = new $this -> modelClass;
        $Companiessmodel -> name = $name;

        $ret = $Companiessmodel -> save(false);
        return ['SaveError' => $ret];
    }

    //http://localhost:8080/v1/companies/delete/id

    public function actionDelete($id)
    {
        $Companiessmodel = new $this->modelClass;
        $ret=$Companiessmodel->deleteAll("id=".$id);
        if($ret)
            return ['DelError' => $ret];
        throw new \yii\web\NotFoundHttpException("Client id not found!");
    }
}
