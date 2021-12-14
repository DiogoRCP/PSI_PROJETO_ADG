<?php

namespace frontend\modules\api\controllers;

use frontend\models\User;
use phpDocumentor\Reflection\PseudoTypes\NonEmptyLowercaseString;
use Yii;
use yii\filters\auth\HttpBasicAuth;
use yii\filters\auth\QueryParamAuth;
use yii\helpers\VarDumper;
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

    public function auth($token)
    {

        $user = User::findIdentityByAccessToken($token);
        if ($user != null) {
            return $user;
        }
        return null;
    }

    public function actionCarsuser()
    {
        if (\Yii::$app->user->can('frontendCrudVehicle')) {
            $CarsModel = new $this->modelClass;
            $recs = $CarsModel::find()->where('userId = ' . \Yii::$app->user->getId())->all();
            return $recs;
        } else {
            return self::noPermission;
        }
    }

    public function actionTotal()
    {
        if (\Yii::$app->user->can('admin')) {
            $Carssmodel = new $this->modelClass;
            $recs = $Carssmodel::find()->all();
            return ['total' => count($recs)];
        } else {
            return self::noPermission;
        }
    }

    public function actionTotaluser()
    {
        if (\Yii::$app->user->can('frontendCrudVehicle')) {
            $Carssmodel = new $this->modelClass;
            $recs = $Carssmodel::find()->where('userId = ' . \Yii::$app->user->getId())->all();
            return ['total' => count($recs)];
        } else {
            return self::noPermission;
        }
    }

    //http://localhost:8080/api/cars/set/3

    public function actionSet($limit)
    {
        if (\Yii::$app->user->can('frontendCrudVehicle')) {
            $Carssmodel = new $this->modelClass;
            $rec = $Carssmodel::find()->limit($limit)->all();
            return ['limite' => $limit, 'Records' => $rec];
        } else {
            return self::noPermission;
        }
    }

// http://localhost:8080/api/cars/post

    public function actionPost()
    {
        $car = json_decode(Yii::$app->request->rawBody);

        $carssmodel = new $this->modelClass;

        $carssmodel->userId = Yii::$app->user->getId();
        $carssmodel->vin = $car->vin;
        $carssmodel->brand = $car->brand;
        $carssmodel->model = $car->model;
        $carssmodel->color = $car->color;
        $carssmodel->carType = $car->cartype;
        $carssmodel->fuelType = $car->fueltype;
        $carssmodel->registration = $car->registration;
        $carssmodel->modelyear = $car->modelyear;
        $carssmodel->kilometers = $car->kilometers;
        $carssmodel->displacement = $car->displacement;
        $carssmodel->state = $car->state;

        $ret = $carssmodel->save(false);
        return ['SaveError' => $ret];
    }
}
