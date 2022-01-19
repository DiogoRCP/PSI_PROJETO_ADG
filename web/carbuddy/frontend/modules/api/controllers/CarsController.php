<?php

namespace frontend\modules\api\controllers;

use common\models\User;
use Exception;
use frontend\models\Cars;
use Yii;
use yii\db\IntegrityException;
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
        return $user;
    }

    public function checkAccess($action, $model = null, $params = [])
    {
        if (!Yii::$app->user->can('admin')) {
            throw new ForbiddenHttpException(self::noPermission);
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
        }
        throw new ForbiddenHttpException(self::noPermission);
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
        }
        throw new ForbiddenHttpException(self::noPermission);
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
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionSet($limit)
    {
        if (Yii::$app->user->can('admin')) {
            $Carssmodel = new $this->modelClass;
            $rec = $Carssmodel::find()->limit($limit)->all();
            return ['limite' => $limit, 'Records' => $rec];
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * Garantir que o utilizador só adiciona os seus proprios carros
     * @throws ForbiddenHttpException
     */
    public function actionPost()
    {
        if (Yii::$app->user->can('frontendCrudVehicle')) {
            $car = Yii::$app->request->post();

            $carssmodel = new $this->modelClass;

            $carssmodel->userId = Yii::$app->user->getId();
            $carssmodel->vin = $car['vin'];
            $carssmodel->brand = $car['brand'];
            $carssmodel->model = $car['model'];
            $carssmodel->color = $car['color'];
            $carssmodel->carType = $car['carType'];
            $carssmodel->fuelType = $car['fuelType'];
            $carssmodel->registration = $car['registration'];
            $carssmodel->modelyear = $car['modelyear'];
            $carssmodel->kilometers = $car['kilometers'];
            $carssmodel->displacement = $car['displacement'];
            $carssmodel->state = $car['state'];

            $ret = $carssmodel->save();
            return ['SaveError' => $ret];
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionPut($id)
    {
        if (Yii::$app->user->can('frontendCrudVehicle')) {
            $car = Yii::$app->request->post();

            $carssmodel = Cars::findOne($id);

            if (Cars::findOne($id)->userId === Yii::$app->user->getId()) {
                if(isset($car['vin']))$carssmodel->vin = $car['vin'];
                if(isset($car['brand']))$carssmodel->brand = $car['brand'];
                if(isset($car['model']))$carssmodel->model = $car['model'];
                if(isset($car['color']))$carssmodel->color = $car['color'];
                if(isset($car['carType']))$carssmodel->carType = $car['carType'];
                if(isset($car['fuelType']))$carssmodel->fuelType = $car['fuelType'];
                if(isset($car['registration']))$carssmodel->registration = $car['registration'];
                if(isset($car['modelyear']))$carssmodel->modelyear = $car['modelyear'];
                if(isset($car['kilometers']))$carssmodel->kilometers = $car['kilometers'];
                if(isset($car['displacement']))$carssmodel->displacement = $car['displacement'];
                if(isset($car['state']))$carssmodel->state = $car['state'];

                $rec = $carssmodel->save();
                return ['Save' => $rec];
            }
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionDeleted($id)
    {
        if (Yii::$app->user->can('frontendCrudVehicle')) {
            $CarsModel = new $this->modelClass;
            try {
                $recs = $CarsModel->deleteAll("id=" . $id . " and userId = " . Yii::$app->user->getId());
                return ['DEL' => $recs];

            } catch (IntegrityException $e) {
                Yii::$app->session->setFlash('error', 'You can´t delete this Car');
            }
        }
        throw new ForbiddenHttpException(self::noPermission);
    }
}
