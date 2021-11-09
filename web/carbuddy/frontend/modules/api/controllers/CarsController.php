<?php

namespace frontend\modules\api\controllers;
use frontend\models\User;
use phpDocumentor\Reflection\PseudoTypes\NonEmptyLowercaseString;
use yii\filters\auth\HttpBasicAuth;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;
class CarsController extends ActiveController
{
    public $modelClass = 'frontend\models\Cars';
    const noPermission = 'Access denied';

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
        if(\Yii::$app->user->can('frontendCrudVehicle')) {
            return $this->render('index');
        }else{
            return self::noPermission;
        }
    }

    public function actionTotal(){
        if(\Yii::$app->user->can('admin')) {
            $Carssmodel = new $this -> modelClass;
            $recs = $Carssmodel::find() -> all();
            return ['total' => count($recs)];
        }else{
            return self::noPermission;
        }
    }

    public function actionTotaluser(){
        if(\Yii::$app->user->can('frontendCrudVehicle')) {
            $Carssmodel = new $this -> modelClass;
            $recs = $Carssmodel::find()->where('userId = ' . \Yii::$app->user->getId()) -> all();
            return ['total' => count($recs)];
        }else{
            return self::noPermission;
        }
    }

    //http://localhost:8080/v1/cars/set/3

    public function actionSet($limit){
        if(\Yii::$app->user->can('frontendCrudVehicle')) {
            $Carssmodel = new $this->modelClass;
            $rec = $Carssmodel::find()->limit($limit)->all();
            return ['limite' => $limit, 'Records' => $rec];
        }else{
            return self::noPermission;
        }
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
        if($ret) {
            if (\Yii::$app->user->getId() == $ret->userId) {
                return ['DelError' => $ret];
            }else{
                return self::noPermission;
            }
        }
        throw new \yii\web\NotFoundHttpException("Car id not found!");
    }
}
