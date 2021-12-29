<?php

namespace frontend\modules\api\controllers;

use common\models\User;
use frontend\models\Cars;
use frontend\models\Contributors;
use Yii;
use yii\db\IntegrityException;
use yii\filters\auth\QueryParamAuth;
use yii\helpers\VarDumper;
use yii\rest\ActiveController;
use yii\web\ForbiddenHttpException;

class RepairsController extends ActiveController
{
    public $modelClass = 'frontend\models\Repairs';
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
        if (!Yii::$app->user->can('admin')) {
            throw new ForbiddenHttpException(self::noPermission);
        }
    }

    /** Retorna todas as reparações de um determinado colaborador **/
    /**
     * @throws ForbiddenHttpException
     */
    public function actionRepaircontributor()
    {
        if (Yii::$app->user->can('frontendCrudRepair')) {
            $contributorModel = Contributors::findOne(Yii::$app->user->getId());
            $RepairModel = new $this->modelClass;
            $recs = $RepairModel::find()->where('contributorId = ' . $contributorModel->id)->all();
            return $recs;
        } else {
            throw new ForbiddenHttpException(self::noPermission);
        }
    }

    // Todo ficámos aqui!
    public function actionPost()
    {
        if (Yii::$app->user->can('frontendCrudRepair')) {
            $repair = Yii::$app->request->post();

            $contributorModel = Contributors::findOne(Yii::$app->user->getId());
            $repairsmodel = new $this->modelClass;

            $repairsmodel->contributorId = $contributorModel->id;
            $repairsmodel->kilometers = $repair['kilometers'];
            $repairsmodel->repairdate = $repair['repairdate'];
            $repairsmodel->repairdescription = $repair['repairdescription'];
            $repairsmodel->state = $repair['state'];
            $repairsmodel->repairType = $repair['repairType'];
            $repairsmodel->carId = $repair['carId'];

            $ret = $repairsmodel->save();
            return ['SaveError' => $ret];
        } else {
            throw new ForbiddenHttpException(self::noPermission);
        }
    }

    public function actionDeleted($id)
    {
        if (Yii::$app->user->can('frontendCrudRepair')) {
            $contributorModel = Contributors::findOne(Yii::$app->user->getId());
            $repairsmodel = new $this->modelClass;
            try {
                $recs = $repairsmodel->deleteAll("id=" . $id . " and userId = " . $contributorModel->id);
                return ['SaveError' => $recs];

            } catch (IntegrityException $e) {
                Yii::$app->session->setFlash('error', 'You can´t delete this Car');
            }
        } else {
            throw new ForbiddenHttpException(self::noPermission);
        }
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionHistory($car)
    {
        $CarModel = Cars::findOne($car);
        if($CarModel->userId === Yii::$app->user->getId()) {
            $Repairssmodel = new $this->modelClass;
            $recs = $Repairssmodel::find()->where("carId = " . $car)->all();
            return $recs;
        }else{
            throw new ForbiddenHttpException(self::noPermission);
        }
    }

    public function actionTotal()
    {
        $Repairssmodel = new $this->modelClass;
        $recs = $Repairssmodel::find()->all();
        return ['total' => count($recs)];
    }


    public function actionSet($limit)
    {
        $Repairssmodel = new $this->modelClass;
        $rec = $Repairssmodel::find()->limit($limit)->all();
        return ['limite' => $limit, 'Records' => $rec];
    }

}
