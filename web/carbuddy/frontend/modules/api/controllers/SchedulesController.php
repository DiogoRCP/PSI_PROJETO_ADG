<?php

namespace frontend\modules\api\controllers;
use backend\models\User;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;
class SchedulesController extends ActiveController
{
    public $modelClass = 'frontend\models\Schedules';

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
        $Schedulessmodel = new $this -> modelClass;
        $recs = $Schedulessmodel::find() -> all();
        return ['total' => count($recs)];
    }

    //http://localhost:8080/v1/schedules/set/3

    public function actionSet($limit){
        $Schedulessmodel = new $this -> modelClass;
        $rec = $Schedulessmodel::find() -> limit($limit) -> all();
        return ['limite' => $limit, 'Records' => $rec ];
    }

// http://localhost:8080/v1/schedules/post

    public function actionPost() {

        $name=\Yii::$app -> request -> post('name');

        $Schedulessmodel = new $this -> modelClass;
        $Schedulessmodel -> name = $name;

        $ret = $Schedulessmodel -> save(false);
        return ['SaveError' => $ret];
    }

    //http://localhost:8080/v1/schedules/delete/id

    public function actionDelete($id)
    {
        $Schedulessmodel = new $this->modelClass;
        $ret=$Schedulessmodel->deleteAll("id=".$id);
        if($ret)
            return ['DelError' => $ret];
        throw new \yii\web\NotFoundHttpException("Client id not found!");
    }
}
