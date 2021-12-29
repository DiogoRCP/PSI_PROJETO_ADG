<?php

namespace frontend\modules\api\controllers;

use common\models\User;
use frontend\models\Cars;
use frontend\models\Contributors;
use frontend\models\Schedules;
use Yii;
use yii\db\IntegrityException;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;
use yii\web\ForbiddenHttpException;

class SchedulesController extends ActiveController
{
    public $modelClass = 'frontend\models\Schedules';
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
        throw new ForbiddenHttpException(self::noPermission);
    }

    public function actionTotal()
    {
        if (Yii::$app->user->can('admin')) {
            $Schedulessmodel = new $this->modelClass;
            $recs = $Schedulessmodel::find()->all();
            return ['total' => count($recs)];
        } else {
            return self::noPermission;
        }
    }

    public function actionSet($limit)
    {
        if (Yii::$app->user->can('admin')) {
            $Schedulessmodel = new $this->modelClass;
            $rec = $Schedulessmodel::find()->limit($limit)->all();
            return ['limite' => $limit, 'Records' => $rec];
        } else {
            return self::noPermission;
        }
    }

    public function actionPost()
    {
        if (Yii::$app->user->can('frontendCrudSchedulesClient')) {
            $schedule = Yii::$app->request->post();

            $carModel = Cars::findOne($schedule['carId']);
            if ($carModel->userId === Yii::$app->user->getId()) {
                $schedulemodel = new $this->modelClass;

                $schedulemodel->schedulingdate = $schedule['schedulingdate'];
                $schedulemodel->repairdescription = $schedule['repairdescription'];
                $schedulemodel->state = "Pending";
                $schedulemodel->repairtype = $schedule['repairtype'];
                $schedulemodel->carId = $schedule['carId'];
                $schedulemodel->companyId = $schedule['companyId'];

                $ret = $schedulemodel->save();
                return ['Save' => $ret];
            }
        }
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionDeleteclient($id)
    {
        if (Yii::$app->user->can('frontendCrudSchedulesClient')) {
            $schedulemodel = new $this->modelClass;
            try {
                $scheduleOldModel = Schedules::findOne($id);
                $car = $scheduleOldModel->car;
                if ($car->userId === Yii::$app->user->getId()) {
                    $recs = $schedulemodel->deleteAll("id=" . $id);
                    return ['DEL' => $recs];
                }

            } catch (IntegrityException $e) {
                throw new ForbiddenHttpException(self::noPermission);
            }
        } else {
            throw new ForbiddenHttpException(self::noPermission);
        }
    }

    public function actionDeletecontributor($id)
    {
        if (Yii::$app->user->can('frontendCrudSchedulesCollaborator')) {
            $schedulemodel = new $this->modelClass;
            try {
                $scheduleOldModel = Schedules::findOne($id);
                $contributor = Contributors::find()->where("userId = " . Yii::$app->user->getId())->one();
                if ($contributor->companyId === $scheduleOldModel->companyId) {
                    $recs = $schedulemodel->deleteAll("id=" . $id);
                    return ['DEL' => $recs];
                }

            } catch (IntegrityException $e) {
                throw new ForbiddenHttpException(self::noPermission);
            }
        } else {
            throw new ForbiddenHttpException(self::noPermission);
        }
    }
}
