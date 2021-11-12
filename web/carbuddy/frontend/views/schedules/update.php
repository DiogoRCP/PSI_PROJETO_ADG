<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model frontend\models\Schedules */
/* @var $modelCompanies frontend\models\Companies */
/* @var $modelCars frontend\models\Cars */

$this->title = 'Update Schedules: ' . $model->id;
$this->params['breadcrumbs'][] = ['label' => 'Schedules', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="schedules-update">
    <div class="schedules-content">
    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model, 'modelCompanies' => $modelCompanies, 'modelCars' => $modelCars,
    ]) ?>
    </div>
</div>
