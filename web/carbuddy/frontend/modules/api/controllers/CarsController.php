<?php

namespace frontend\modules\api\controllers;
use frontend\models\User;
use yii\filters\auth\HttpBasicAuth;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;
class CarsController extends ActiveController
{
    public $modelClass = 'app\models\Cars';

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
        $Carssmodel = new $this -> modelClass;
        $recs = $Carssmodel::find() -> all();
        return ['total' => count($recs)];
    }

    //http://localhost:8080/v1/cars/set/3

    public function actionSet($limit){
        $Carssmodel = new $this -> modelClass;
        $rec = $Carssmodel::find() -> limit($limit) -> all();
        return ['limite' => $limit, 'Records' => $rec ];
    }

// http://localhost:8080/v1/cars/post

    public function actionPost() {

        $name=\Yii::$app -> request -> post('name');

        $Carssmodel = new $this -> modelClass;
        $Carssmodel -> name = $name;

        $ret = $Carssmodel -> save(false);
        return ['SaveError' => $ret];
    }

    //http://localhost:8080/v1/cars/delete/id

    public function actionDelete($id)
    {
        $Carssmodel = new $this->modelClass;
        $ret=$Carssmodel->deleteAll("id=".$id);
        if($ret)
            return ['DelError' => $ret];
        throw new \yii\web\NotFoundHttpException("Client id not found!");
    }
}
