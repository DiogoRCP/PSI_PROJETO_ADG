<?php

namespace frontend\modules\api\controllers;

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
}
