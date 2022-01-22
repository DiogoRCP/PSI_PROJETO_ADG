<?php

namespace frontend\modules\api\controllers;

use common\models\User;
use frontend\models\Cars;
use frontend\models\Contributors;
use frontend\models\Repairs;
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
        return $user;
    }

    public function checkAccess($action, $model = null, $params = [])
    {
        if (!Yii::$app->user->can('admin') or $action != "index") {
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
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionPost()
    {
        if (Yii::$app->user->can('frontendCrudRepair')) {
            $repair = Yii::$app->request->post();

            $contributorModel = Contributors::find()->where("userId=".Yii::$app->user->getId())->one();
            $repairsmodel = new $this->modelClass;

            $repairsmodel->contributorId = $contributorModel->id;
            $repairsmodel->kilometers = $repair['kilometers'];
            $repairsmodel->repairdate = $repair['repairdate'];
            $repairsmodel->repairdescription = $repair['repairdescription'];
            $repairsmodel->state = $repair['state'];
            $repairsmodel->repairtype = $repair['repairtype'];
            $repairsmodel->carId = $repair['carId'];

            $ret = $repairsmodel->save();
            return ['Save' => $ret];
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionPut($id)
    {
        if (Yii::$app->user->can('frontendCrudRepair')) {
            $repair = Yii::$app->request->post();

            $contributorModel = Contributors::find()->where("userId=".Yii::$app->user->getId())->one();
            $repairsmodel = Repairs::findOne($id);

            if ($repairsmodel->contributorId === $contributorModel->id) {
                if(isset($repair['kilometers']))$repairsmodel->kilometers = $repair['kilometers'];
                if(isset($repair['repairdate']))$repairsmodel->repairdate = $repair['repairdate'];
                if(isset($repair['repairdescription']))$repairsmodel->repairdescription = $repair['repairdescription'];
                if(isset($repair['state']))$repairsmodel->state = $repair['state'];
                if(isset($repair['repairtype']))$repairsmodel->repairtype = $repair['repairtype'];
                if(isset($repair['carId']))$repairsmodel->carId = $repair['carId'];

                $rec = $repairsmodel->save();
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
        if (Yii::$app->user->can('frontendCrudRepair')) {
            $contributorModel = Contributors::findOne(Yii::$app->user->getId());
            $repairsmodel = new $this->modelClass;
            try {
                $recs = $repairsmodel->deleteAll("id=" . $id . " and contributorId = " . $contributorModel->id);
                return ['Del' => $recs];

            } catch (IntegrityException $e) {
                Yii::$app->session->setFlash('error', 'You can´t delete this Repair');
            }
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionHistory($car)
    {
        $CarModel = Cars::findOne($car);
        if ($CarModel->userId === Yii::$app->user->getId()) {
            $Repairssmodel = new $this->modelClass;
            $recs = $Repairssmodel::find()->where("carId = " . $car)->orderBy("repairdate")->all();
            return $recs;
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionTotal()
    {
        if(Yii::$app->user->can("admin")) {
            $Repairssmodel = new $this->modelClass;
            $recs = $Repairssmodel::find()->all();
            return ['total' => count($recs)];
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionSet($limit)
    {
        if(Yii::$app->user->can("admin")) {
            $Repairssmodel = new $this->modelClass;
            $rec = $Repairssmodel::find()->limit($limit)->all();
            return ['limite' => $limit, 'Records' => $rec];
        }
        throw new ForbiddenHttpException(self::noPermission);
    }

}
