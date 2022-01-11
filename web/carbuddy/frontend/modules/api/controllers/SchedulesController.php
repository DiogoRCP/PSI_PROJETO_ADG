<?php

namespace frontend\modules\api\controllers;

use common\models\User;
use frontend\models\Cars;
use frontend\models\Contributors;
use frontend\models\Schedules;
use Yii;
use yii\db\IntegrityException;
use yii\filters\auth\QueryParamAuth;
use yii\helpers\VarDumper;
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
        return $user;
    }

    public function checkAccess($action, $model = null, $params = [])
    {
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionTotal()
    {
        if (Yii::$app->user->can('admin')) {
            $Schedulessmodel = new $this->modelClass;
            $recs = $Schedulessmodel::find()->all();
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
            $Schedulessmodel = new $this->modelClass;
            $rec = $Schedulessmodel::find()->limit($limit)->all();
            return ['limite' => $limit, 'Records' => $rec];
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
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
        throw new ForbiddenHttpException(self::noPermission);
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
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
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
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionPutclient($id)
    {
        if (Yii::$app->user->can('frontendCrudSchedulesClient')) {
            $schedule = Yii::$app->request->post();
            $scheduleModel = Schedules::findOne($id);

            $car = $scheduleModel::findOne($id)->car;
            if ($car->userId === Yii::$app->user->getId()) {
                $scheduleModel->schedulingdate = $schedule['schedulingdate'];
                $scheduleModel->repairdescription = $schedule['repairdescription'];
                $scheduleModel->repairtype = $schedule['repairtype'];
                $scheduleModel->carId = $schedule['carId'];
                $rec = $scheduleModel->save();
                return ['PUT' => $rec];
            }
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionPutcontributor($id)
    {
        if (Yii::$app->user->can('frontendCrudSchedulesCollaborator')) {
            $schedule = Yii::$app->request->post();
            $scheduleModel = Schedules::findOne($id);

            $company = $scheduleModel::findOne($id)->companyId;
            $contributor = Contributors::find()->where("userId = " . Yii::$app->user->getId())->one();
            if ($company === $contributor->companyId) {
                $scheduleModel->schedulingdate = $schedule['schedulingdate'];
                $scheduleModel->state = $schedule['state'];
                $rec = $scheduleModel->save();
                return ['PUT' => $rec];
            }
        }
        throw new ForbiddenHttpException(self::noPermission);
    }


    /**
     * @throws ForbiddenHttpException
     */
    public function actionGetschedulesclient()
    {
        if (Yii::$app->user->can('frontendCrudSchedulesClient')) {
            $cars = Cars::find()->where("userId = " . Yii::$app->user->getId())->all();
            $recs = [];
            foreach($cars as $car){
                foreach ($car->getSchedules() as $schedule){
                    $objSchedule = [
                        'id' => $schedule->id,
                        'currentdate' => $schedule->currentdate,
                        'schedulingdate' => $schedule->schedulingdate,
                        'repairdescription' => $schedule->repairdescription,
                        'state' => $schedule->state,
                        'repairtype' => $schedule->repairtype,
                        'company' => $schedule->getCompany()->one()->companyname,
                        'carregistration' => $car->registration,
                        'carbrand' => $car->brand,
                        'carmodel' => $car->model
                    ];
                    $recs[] = $objSchedule;
                }

            }
            return $recs;
        }
        throw new ForbiddenHttpException(self::noPermission);
    }
}
