<?php

namespace frontend\modules\api\controllers;
use app\models\User;
use yii\filters\auth\HttpBasicAuth;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;
class RepairsController extends ActiveController
{
    public $modelClass = 'frontend\models\Repairs';

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

    public function actionHistory($car){

    }

    public function actionTotal(){
        $Repairssmodel = new $this -> modelClass;
        $recs = $Repairssmodel::find() -> all();
        return ['total' => count($recs)];
    }

    //http://localhost:8080/v1/repairs/set/3

    public function actionSet($limit){
        $Repairssmodel = new $this -> modelClass;
        $rec = $Repairssmodel::find() -> limit($limit) -> all();
        return ['limite' => $limit, 'Records' => $rec ];
    }

// http://localhost:8080/v1/repairs/post

    public function actionPost() {

        $name=\Yii::$app -> request -> post('name');

        $Repairssmodel = new $this -> modelClass;
        $Repairssmodel -> name = $name;

        $ret = $Repairssmodel -> save(false);
        return ['SaveError' => $ret];
    }

    //http://localhost:8080/v1/repairs/delete/id

    public function actionDelete($id)
    {
        $Repairssmodel = new $this->modelClass;
        $ret=$Repairssmodel->deleteAll("id=".$id);
        if($ret)
            return ['DelError' => $ret];
        throw new \yii\web\NotFoundHttpException("Client id not found!");
    }
}
