<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model frontend\models\Repairs */
/* @var $modelCars frontend\models\Cars */
/* @var $modelUsers backend\models\Users */
/* @var $modelContributor frontend\models\Contributors */

$this->title = 'Create Repair';
//$this->params['breadcrumbs'][] = ['label' => 'Repairs', 'url' => ['index']];
//$this->params['breadcrumbs'][] = $this->title;
?>
<div class="repairs-create">
    <div class = "repairs-content">
        <img src="../images/logo_white.png" width="80">
    <h1><?= Html::encode($this->title) ?></h1>
    <p>
        <?= Html::a('Back', ['index'], ['class' => 'btn btn-dark']) ?>
    </p>
    <?= $this->render('_form', [
        'model' => $model, 'modelCars' => $modelCars
    ]) ?>
    </div>
</div>
