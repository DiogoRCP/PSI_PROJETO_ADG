<?php

namespace frontend\modules\api\controllers;

use backend\models\User;
use Yii;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;
use yii\web\ForbiddenHttpException;

/** Este controller serve para permitir que na página de login da aplicação
 * dê para ver a lista das empresas que colaboram connosco *
 */
class CompanieslistController extends ActiveController
{
    public $modelClass = 'backend\models\Companies';
    const noPermission = 'Access denied';

    public function checkAccess($action, $model = null, $params = [])
    {
        if ($action !== 'index')
            throw new ForbiddenHttpException(self::noPermission);
    }

    public function actionTotal()
    {
        $Companiessmodel = new $this->modelClass;
        $recs = $Companiessmodel::find()->all();
        return ['total' => count($recs)];
    }

    /**
     * @throws ForbiddenHttpException
     */
    public function actionSet($limit)
    {
        $Companiessmodel = new $this->modelClass;
        $rec = $Companiessmodel::find()->limit($limit)->all();
        return ['limite' => $limit, 'Records' => $rec];
    }
}
