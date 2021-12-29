<?php

namespace frontend\modules\api\controllers;
use backend\models\User;
use frontend\models\Schedules;
use Yii;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;
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
        if (Yii::$app->user->can('admin')) {
            $Schedulessmodel = new $this -> modelClass;
            $recs = $Schedulessmodel::find() -> all();
            return ['total' => count($recs)];
        } else {
            return self::noPermission;
        }
    }

    //http://localhost:8080/v1/schedules/set/3

    public function actionSet($limit){
        if (Yii::$app->user->can('admin')) {
            $Schedulessmodel = new $this -> modelClass;
            $rec = $Schedulessmodel::find() -> limit($limit) -> all();
        return ['limite' => $limit, 'Records' => $rec ];
        } else {
            return self::noPermission;
        }
    }

// http://localhost:8080/v1/schedules/post

    public function actionPost() {
        $schedule = Yii::$app->request->post();

        $schedulemodel = new $this->modelClass;

        $schedulemodel->schedulingdate = $schedule['schedulingdate'];
        $schedulemodel->repairdescription = $schedule['repairdescription'];
        $schedulemodel->state = $schedule['state'];
        $schedulemodel->repairtype = $schedule['repairtype'];
        $schedulemodel->carId = $schedule['carId'];
        $schedulemodel->carId = $schedule['companyId'];

        $ret = $schedulemodel->save();
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
