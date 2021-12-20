<?php

namespace frontend\modules\api\controllers;

use common\models\User;
use Yii;
use yii\filters\auth\QueryParamAuth;
use yii\helpers\VarDumper;
use yii\rest\ActiveController;
use yii\web\ForbiddenHttpException;

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

    public function checkAccess($action, $model = null, $params = [])
    {
        if ($action === 'index' or $action === 'create' or $action === 'delete') {
            if (!Yii::$app->user->can('admin')) {
                throw new ForbiddenHttpException(self::noPermission);
            }
        } else {
            if (!Yii::$app->user->can('frontendCrudVehicle')) {
                throw new ForbiddenHttpException(self::noPermission);
            }
        }
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionCarsuser()
    {
        if (Yii::$app->user->can('frontendCrudVehicle')) {
            $CarsModel = new $this->modelClass;
            $recs = $CarsModel::find()->where('userId = ' . Yii::$app->user->getId())->all();
            return $recs;
        }else{
            throw new ForbiddenHttpException(self::noPermission);
        }
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionTotal()
    {
        if (Yii::$app->user->can('admin')) {
            $Carssmodel = new $this->modelClass;
            $recs = $Carssmodel::find()->all();
            return ['total' => count($recs)];
        }else{
            throw new ForbiddenHttpException(self::noPermission);
        }
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionTotaluser()
    {
        if (Yii::$app->user->can('frontendCrudVehicle')) {
            $Carssmodel = new $this->modelClass;
            $recs = $Carssmodel::find()->where('userId = ' . Yii::$app->user->getId())->all();
            return ['total' => count($recs)];
        }else{
            throw new ForbiddenHttpException(self::noPermission);
        }
    }

    //http://localhost:8080/api/cars/set/3

    /**
     * @throws ForbiddenHttpException
     */
    public function actionSet($limit)
    {
        if (Yii::$app->user->can('admin')) {
            $Carssmodel = new $this->modelClass;
            $rec = $Carssmodel::find()->limit($limit)->all();
            return ['limite' => $limit, 'Records' => $rec];
        }else{
            throw new ForbiddenHttpException(self::noPermission);
        }
    }

// http://localhost:8080/api/cars/post

    /**
     * Garantir que o utilizador só adiciona os seus proprios carros
     * @throws ForbiddenHttpException
     */
    public function actionPost()
    {
        if (Yii::$app->user->can('frontendCrudVehicle')) {
            $car = json_decode(Yii::$app->request->rawBody);

            VarDumper::dump($car);
            exit();
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
        }else{
            throw new ForbiddenHttpException(self::noPermission);
        }
    }

    public function actionDelete($id){
        if (Yii::$app->user->can('frontendCrudVehicle')) {
            $CarsModel = new $this->modelClass;
            $recs = $CarsModel::find()->where('userId = ' . Yii::$app->user->getId())->where('id = ' . $id)->all();
            $recs->delete();
        }else{
            throw new ForbiddenHttpException(self::noPermission);
        }
    }
}
